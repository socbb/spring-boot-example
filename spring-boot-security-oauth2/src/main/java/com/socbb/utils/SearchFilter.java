package com.socbb.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

/**
 * 搜索过滤器
 */
public class SearchFilter {

    public enum Operator {
        EQ, NEQ, LIKE, CONTAIN, STARTWITH, ENDWITH, GT, LT, GTE, LTE, IN, ISNULL, ISNOTNULL
    }

    public enum Type {
        String, Integer, Long, Float, Double, BigDecimal, BigInteger, Boolean, Date, Timestamp;

        public static Object[] convert(String[] strArray, Type type, Operator operator) {
            if (ArrayUtils.isEmpty(strArray)) {
                return null;
            }
            List<Object> list = new ArrayList<Object>();
            for (int i = 0, len = strArray.length; i < len; i++) {
                String str = strArray[i];
                if (StringUtils.isBlank(str)) {
                    continue;
                }
                switch (type) {
                    case Integer:
                        list.add(NumberUtils.createInteger(str));
                        break;
                    case Long:
                        list.add(NumberUtils.createLong(str));
                        break;
                    case Float:
                        list.add(NumberUtils.createDouble(str));
                        break;
                    case Double:
                        list.add(NumberUtils.createDouble(str));
                        break;
                    case BigDecimal:
                        list.add(NumberUtils.createBigDecimal(str));
                        break;
                    case BigInteger:
                        list.add(NumberUtils.createBigInteger(str));
                        break;
                    case Boolean:
                        list.add(java.lang.Boolean.valueOf(str));
                        break;
                    case Date:
                        LocalDateTime dt = LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        if (str.length() <= 10 && operator == Operator.LTE) {
                            dt = dt.plusDays(1);
                            dt = dt.minusMinutes(1);
                        }
                        list.add(dt);
                        break;
                    case Timestamp:
                        list.add(new java.sql.Timestamp(LocalDateTime.parse(str).toInstant(ZoneOffset.of("+8")).toEpochMilli()));
                        break;
                    default:
                        list.add(str);
                }
            }
            return list.toArray();
        }
    }

    public String fieldName;

    public Object[] value;

    public Operator operator;

    public SearchFilter(String fieldName, Operator operator, Object[] value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    public static <T> Specification<T> spec(final Collection<SearchFilter> filters, final Class<T> clazz) {
        return (Specification<T>) (root, query, builder) -> {
            if (CollectionUtils.isNotEmpty(filters)) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                for (SearchFilter filter : filters) {
                    // nested path translate, 如Task的名为"user.name"的filedName,
                    // 转换为Task.user.name属性
                    String[] names = StringUtils.split(filter.fieldName, ".");
                    Path exp = root;
                    for (int i = 0; i < names.length; i++) {
                        String name = names[i];
                        if (name.startsWith("J")) {
                            name = name.substring(1);
                            exp = ((From) exp).join(name);
                        } else {
                            exp = exp.get(name);
                        }
                    }

                    // logic operator
                    switch (filter.operator) {
                        case EQ:
                            predicates.add(builder.equal(exp, filter.value[0]));
                            break;
                        case NEQ:
                            predicates.add(builder.notEqual(exp, filter.value[0]));
                            break;
                        case LIKE:
                            predicates.add(builder.like(exp, filter.value[0].toString()));
                            break;
                        case CONTAIN:
                            predicates.add(builder.like(exp, "%" + filter.value[0] + "%"));
                            break;
                        case STARTWITH:
                            predicates.add(builder.like(exp, filter.value[0] + "%"));
                            break;
                        case ENDWITH:
                            predicates.add(builder.like(exp, "%" + filter.value[0]));
                            break;
                        case GT:
                            predicates.add(builder.greaterThan(exp, (Comparable) filter.value[0]));
                            break;
                        case LT:
                            predicates.add(builder.lessThan(exp, (Comparable) filter.value[0]));
                            break;
                        case GTE:
                            predicates.add(builder.greaterThanOrEqualTo(exp, (Comparable) filter.value[0]));
                            break;
                        case LTE:
                            predicates.add(builder.lessThanOrEqualTo(exp, (Comparable) filter.value[0]));
                            break;
                        case IN:
                            predicates.add(exp.in(filter.value));
                            break;
                        case ISNULL:
                            predicates.add(builder.isNull(exp));
                            break;
                        case ISNOTNULL:
                            predicates.add(builder.isNotNull(exp));
                            break;
                    }
                }

                // 将所有条件用 and 联合起来
                if (predicates.size() > 0) {
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            }
            return builder.conjunction();
        };
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     */
    public static Map<String, SearchFilter> parse(Map<String, String[]> params) {
        if (params == null || params.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        for (Entry<String, String[]> entry : params.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            String[] values = entry.getValue();
            if (ArrayUtils.isEmpty(values)) {
                continue;
            }
            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if (names.length < 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            Operator operator = Operator.valueOf(names[0]);
            String filedName = names[1];
            Type type = Type.String;
            if (names.length >= 3) {
                type = Type.valueOf(names[2]);
            }
            Object[] ovalues = Type.convert(values, type, operator);
            if (ArrayUtils.isEmpty(ovalues)) {
                continue;
            }
            // 创建searchFilter
            SearchFilter filter = new SearchFilter(filedName, operator, ovalues);
            filters.put(key, filter);
        }
        return filters;
    }
}
