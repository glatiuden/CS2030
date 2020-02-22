class Server {
    private int serverId;
    private double nextServingTime;
    private boolean isWait;

    public Server(int id, double nextServingTime) {
        this.serverId = id;
        this.nextServingTime = nextServingTime;
        this.isWait = false;
    }

    public Server(int id) {
        this.serverId = id;
        this.nextServingTime = 0;
        this.isWait = false;
    }

    public Server(Server server) {
        this.serverId = server.getServerId();
        this.nextServingTime = server.getNextServingTime();
        this.isWait = false;
    }

    public Server() {
    }

    public boolean getWait() {
        return this.isWait;
    }

    public void setWait(boolean isWait) {
        this.isWait = isWait;
    }

    public double getNextServingTime() {
        return this.nextServingTime;
    }

    public void setNextServingTime(double nextServingTime) {
        this.nextServingTime = nextServingTime;
    }

    public int getServerId() {
        return this.serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public boolean isEmpty() {
        return this.serverId == 0;
    }
}