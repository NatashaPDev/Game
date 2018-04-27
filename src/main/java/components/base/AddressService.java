package components.base;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AddressService {
    private Map<Class<?>, Address> addresses = new HashMap<Class<?>, Address>();

    public Address getAddress(Class<?> abonentClass) {
        return addresses.get(abonentClass);
    }

    public void setAbonentAddress(Abonent abonent) {
        addresses.put(abonent.getClass(), abonent.getAddress());
    }
}