package ru.ilyin;

import ru.ilyin.announcers.Announcer;
import ru.ilyin.core.ApplicationContext;
import ru.ilyin.core.configurations.JavaConfig;
import ru.ilyin.core.ObjectFactory;
import ru.ilyin.entity.Room;
import ru.ilyin.core.annotations.InjectByType;
import ru.ilyin.policemans.Policeman;

import java.util.Map;

@Deprecated
public class CoronaDesinfector {
    @InjectByType
    private Announcer announcer;
    @InjectByType
    private Policeman policeman;

    public void start(Room room) {
        announcer.announce("Начинаем дезинфекцию, все вон!");
        policeman.makePeopleLeaveRoom();
        desunfect(room);
        announcer.announce("Рискните зайти обратно");

    }

    private void desunfect(Room room) {
        System.out.println("Зачитывается молитва: корона изыди! - молитва прочитана, вирус низвергнут в ад");
    }

    // нужен для создания и загрузки контекста приложения
    public static class Application {
        public static ApplicationContext run(String packageToScan, Map<Class<?>, Class<?>> ifc2ImplClass) {
            JavaConfig config = new JavaConfig(packageToScan, ifc2ImplClass);
            ApplicationContext context = new ApplicationContext(config);
            ObjectFactory objectFactory = new ObjectFactory(context);
            context.setFactory(objectFactory);
            return context;
        }

        private Application() {
        }
    }
}
