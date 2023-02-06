package com.sivalabs.techbuzz.config.argresolvers;

import com.sivalabs.techbuzz.config.annotations.CurrentUser;
import com.sivalabs.techbuzz.security.SecurityService;
import java.lang.annotation.Annotation;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final SecurityService securityService;

    public CurrentUserArgumentResolver(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return findMethodAnnotation(CurrentUser.class, methodParameter) != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) {
        return securityService.loginUser();
    }

    private <T extends Annotation> T findMethodAnnotation(
            Class<T> annotationClass, MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }
}
