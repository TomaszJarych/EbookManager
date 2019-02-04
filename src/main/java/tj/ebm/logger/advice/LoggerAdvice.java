package tj.ebm.logger.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class LoggerAdvice {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @AfterThrowing(pointcut = "tj.ebm.logger.pointcuts.PointcutsDeclaration.allCrudMethodsFromServicesThatThrowsException()",
            throwing = "exception")
    public void exceptionLogging(JoinPoint joinPoint, Throwable exception) {

        String message = "========>Mehtod" + joinPoint.toShortString() + " throws an Exception " + exception;
        logger.warning(message);

    }
}
