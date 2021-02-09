package com.cola.course.realtimestreaming.course05.common.annotations;


import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS  )
public @interface NotThreadSafe {
}
