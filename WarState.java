

public abstract class WarState {
  protected static WarehouseContext context;
  protected WarState() {
   // context = WarehouseContext.instance();
  }
  public abstract void run();
}
