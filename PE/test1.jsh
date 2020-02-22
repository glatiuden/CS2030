new NormalCab("SHA1234", 5)	
new Booking(new NormalCab("SHA1234", 5), new JustRide(), new Request(20, 3, 1000))	
new Booking(new NormalCab("SHA1234", 5), new TakeACab(), new Request(20, 3, 1000))
new NormalCab("SHA2345", 10)	
new Booking(new NormalCab("SHA2345", 10), new JustRide(), new Request(10, 1, 900))
new Booking(new NormalCab("SHA2345", 10), new TakeACab(), new Request(10, 1, 900))	
Comparable b1 = new Booking(new NormalCab("SHA2345", 10), new JustRide(), new Request(10, 1, 900))
Booking b2 = new Booking(new NormalCab("SHA2345", 10), new TakeACab(), new Request(10, 1, 900))	
b1.compareTo(b2) > 0	
Comparable b3 = new Booking(new NormalCab("SHA1234", 5), new JustRide(), new Request(10, 1, 900))	
Booking b4 = new Booking(new NormalCab("SHA2345", 10), new JustRide(), new Request(10, 1, 900))	
b3.compareTo(b4) < 0	
Car[] cs = {new NormalCab("X", 0), new NormalCab("Y", 5), new NormalCab("Z", 1000)}	
Ride[] ss = {new JustRide(), new TakeACab()}
Request[] rs = {new Request(1, 2, 1), new Request(9, 2, 559), new Request(27, 3, 600)};	
List list = new ArrayList<>();	
for (Car c: cs) for (Ride s: ss) for (Request r: rs) list.add(new Booking(c, s, r));	
Collections.sort(list);	
for (Booking b: list) System.out.println(b);	
/exit