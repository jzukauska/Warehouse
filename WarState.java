

public abstract class WarState {
  protected static WarehouseContext context;
  protected WarState() {
    //context = LibContext.instance();
  }
  public abstract void run();
}
