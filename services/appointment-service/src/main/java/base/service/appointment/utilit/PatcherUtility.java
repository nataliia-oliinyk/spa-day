package base.service.appointment.utilit;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class PatcherUtility {
    public static void objectPatcher(Class<?> modelClass, Object existingObject, Object incompleteObject) throws IllegalAccessException {
        Field[] fields = modelClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(incompleteObject);
            if (value != null) {
                field.set(existingObject, value);
            }
            field.setAccessible(false);
        }
    }
}
