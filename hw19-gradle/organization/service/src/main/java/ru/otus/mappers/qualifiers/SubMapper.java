package ru.otus.mappers.qualifiers;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Qualifier
@Target(ElementType.METHOD)
public @interface SubMapper {
}
