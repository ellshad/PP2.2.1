package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru", new Car("Lightning McQueen", 1)));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car("Mater", 2)));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru", new Car("Doc Hudson", 3)));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru", new Car("Sally Carrera", 4)));

        //Все пользователи с машиной
        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }
        System.out.println("****************************************************");

        //2. Выбрать пользователя, владеющего машиной (по ее модели и серии)

        System.out.println("\n" + userService.getUserByCar("Lightning McQueen", 1) + "\n");

        System.out.println("*****************************************************");

        //3. Нет пользователя с такой машиной
        try {
            System.out.println(userService.getUserByCar("Optimus Prime", 1));
        } catch (NoResultException e) {
            System.out.println("\n" + "Пользователя с такой машиной нет" + "\n");
        }
        context.close();
    }
}
