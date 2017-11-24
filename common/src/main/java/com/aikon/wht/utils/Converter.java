package com.aikon.wht.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author haitao.wang
 *
 * entity转换器.
 */
public class Converter<F, T> {

    private Function<F, T> function;


    public Converter(Function<F, T> function) {
        this.function = function;
    }

    public T convert(F from) {
        return this.function.apply(from);
    }


    public List<T> convert(Collection<F> fromList) {
        return fromList.stream().map(this.function::apply).collect(Collectors.toList());
    }

}
