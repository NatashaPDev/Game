package components.base;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope("prototype")
public class Address {
    static private AtomicInteger abonentIdCreator = new AtomicInteger();
    final private int abonentId;

    public Address(){
        this.abonentId = abonentIdCreator.incrementAndGet();
    }

    public int hashCode(){
        return abonentId;
    }
}
