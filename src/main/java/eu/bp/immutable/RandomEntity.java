package eu.bp.immutable;

import javax.print.attribute.standard.MediaSize;
import java.util.*;
import java.util.stream.*;

public class RandomEntity {
    private static final Random RND = new Random();
    private static final String[] COLORS = new String[]{"Azure", "Crimson", "Emerald", "Fuchsia", "Gold", "Indigo", "Lavender", "Maroon", "Olive", "Turquoise"};
    private static final String[] NAMES = new String[]{"James", "Olivia", "Liam", "Emma", "Noah", "Ava", "Lucas", "Sophia", "Mason", "Isabella"};

    public static List<Car> cars(int pieces) {
        return IntStream.range(0, pieces)
                .mapToObj(i -> new Car())
                .map(c -> { c.setColor(element(COLORS)); return c;})
                .collect(Collectors.toList());
    }

    public static List<Owner> owners(List<Car> cars) {
        return Arrays.stream(NAMES).map(n -> {
            Owner owner = new Owner();
            owner.setCars(new HashSet<>());
            owner.setName(n);
            double carNum = RND.nextInt(cars.size());
            for (int i = cars.size() - 1; i >= 0 && carNum > 0; i--) {
                if (RND.nextDouble() < carNum / (double)i) {
                    owner.getCars().add(cars.get(i));
                    carNum--;
                }
            }
            return owner;
        }).collect(Collectors.toList());
    }

    private static String element(String[] arr) {
        return arr[RND.nextInt(arr.length)];
    }

}
