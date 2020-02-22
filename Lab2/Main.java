import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int noOfCruise = sc.nextInt(); // get user input for no. of cruises
        if (noOfCruise <= 30 && noOfCruise > 0) { // else break the programme
            Cruise[] arr = new Cruise[noOfCruise];
            List<Loader> loaders = new ArrayList<Loader>();
            loaders.add(new Loader(1)); // since noOfCruise > 0, initialise the first loader

            for (int i = 0; i < noOfCruise; i++) {
                String cruiseId = sc.next();
                int timeOfArrival = sc.nextInt();
                if (cruiseId.charAt(0) == ('B')) { // big cruise
                    int noOfLoaders = sc.nextInt();
                    int timeRequired = sc.nextInt();
                    if (noOfLoaders >= 0 && noOfLoaders <= 9 && timeRequired >= 0 && timeRequired <= 99) {
                        BigCruise bigCruise = new BigCruise(cruiseId, timeOfArrival, noOfLoaders, timeRequired);
                        arr[i] = bigCruise;
                    } else { // else break the programme
                        sc.close();
                        break;
                    }
                } else // normal cruise
                {
                    Cruise smallCruise = new Cruise(cruiseId, timeOfArrival);
                    arr[i] = smallCruise;
                }
            }
            sc.close();

            for (Cruise c : arr) { // loop through the cruise array to assigned loader
                for (int i = 0; i < c.getNumLoadersRequired(); i++) {
                    assignedLoaders(c, loaders);
                }
            }
        } else {
            sc.close();
        }
    }

    private static void assignedLoaders(Cruise cruise, List<Loader> loaders) {
        int loaderSize = loaders.size();
        for (int i = 0; i < loaderSize; i++) {
            Loader tryServe = loaders.get(i).serve(cruise); // see whether can the loader serve the cruise
            if (tryServe != null) {
                loaders.set(i, tryServe); // replace the loader in arraylist if it's served
                System.out.println(tryServe.toString());
                break;
            } else if (tryServe == null && (i != loaderSize - 1)) { // if loader (i) can't serve, continue until last
                continue;
            } else {
                int id = loaderSize + 1;
                if (id % 3 == 0) {
                    loaders.add(createRecycledLoader(id, cruise));
                } else {
                    loaders.add(createLoader(id, cruise));
                }
                break;
            }
        }
    }

    private static Loader createLoader(int newLoaderId, Cruise cruise) {
        Loader newLoader = new Loader(newLoaderId, cruise);
        System.out.println(newLoader.toString());
        return newLoader;
    }

    private static RecycledLoader createRecycledLoader(int newLoaderId, Cruise cruise) {
        RecycledLoader newLoader = new RecycledLoader(newLoaderId, cruise);
        System.out.println(newLoader.toString());
        return newLoader;
    }
}
