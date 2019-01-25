package tj.ebm.logger.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutsDeclaration {

    @Pointcut("execution(* tj.ebm.Author.Service.Implementation.AuthorServiceImpl.*(..))")
    public void authorServiceMethods() {
    }

    @Pointcut("execution(* tj.ebm.Book.Service.Implementation.BookServiceImpl.*(..))")
    public void bookServiceMethods() {
    }

    @Pointcut("execution(* tj.ebm.Bookstore.Service.Implementation.BookstoreImpl.*(..))")
    public void bookstoreServiceMethods() {
    }

    @Pointcut("execution(* tj.ebm.Genre.Service.GenreService.*(..))")
    public void genreServiceMethods() {
    }

    @Pointcut("execution(* tj.ebm.User.Service.Implementation.UserServiceImpl.*(..))")
    public void userServiceMethods() {
    }


    @Pointcut("execution(* tj.ebm.*.*.*.*.save(..))")
    public void saveOrUpdateMethod() {
    }

    @Pointcut("execution(* tj.ebm.*.*.*.*.deleteFromDb(..))")
    public void deleteMethod() {
    }

    @Pointcut("execution(* tj.ebm.*.*.*.*.findById(..))")
    public void findByIdMethods() {
    }
}
