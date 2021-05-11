# @Enumerated

`@Enumerated` 是JDK中 `javax.persistence` 包下的注解，用在持久化实体类属性或字段上，表示该属性或字段应该按照枚举类型 `Enum` 来持久化。例如：


```java
public enum Gender {
    MALE,
    FEMALE;
}
```

```java
@Entity
public class Person {
    @Column
    @Enumerated
    private Gender gender;
    
    // 省略其他属性方法，后面也是
}
```

属性或字段按照枚举类型来持久化时，可以分为两种情况：原始类型和字符串类型。

枚举持久化类型可以在 `javax.persistence.EnumType` 类中找到，其中 `ORDINAL` 表示原始类型；`STRING` 表示字符串类型。例如字符串类型的写法如下：

```java
@Entity
public class Person {
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
```

如果没有特别指定类型，枚举属性或字段默认按照 `ORDINAL` 来持久化。

## 1. 原始类型

`ORDINAL` 表示将枚举类按照其原始类型，也就是整数，来持久化。比如：

```java
@Entity
public class Person {
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
}
```

如果按照上述的代码执行，枚举字段 `gender` 将被转成数据库的整型字段（以Mysql为例）：

```
JAVA    <--->  Mysql
gender  <--->  int(11)
```

也就是当 `Person` 对象被持久化到数据库时，枚举字段 `gender` 的值将按照其整数值来保存。

```
Gender  <--->  Mysql
MALE    <--->  0
FEMALE  <--->  1
```

## 2. 字符串类型

`STRING` 表示将枚举类按照字符串类型持久化。

```java
@Entity
public class Person {
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
```

如果按照上述的代码执行，枚举字段 `gender` 将被转成数据库的字符串字段（以Mysql为例）：

```
JAVA    <--->  Mysql
gender  <--->  varchar(255)
```

当 `Person` 对象被持久化到数据库时，枚举字段 `gender` 的值不再按照原始整数来保存，而是将按照其字符串值来保存。

```
Gender  <--->  Mysql
MALE    <--->  MALE
FEMALE  <--->  FEMALE
```

## 3. 总结

总的来说，`@Enumerated` 注解是用来标识枚举字段是按照什么方式来持久化的，也就是保存到数据库的数据类型：

1. `@Enumerated(EnumType.ORDINAL)` 就是保存枚举字段的的整数值（序号）；
2. `@Enumerated(EnumType.STRING)` 就是保存枚举字段的字符串值。

另外，不指定类型的 `@Enumerated` 等于 `@Enumerated(EnumType.ORDINAL)` 。