package il.co.runnerdevice.Pojo;

public class ItemSyncGeneric<T> extends ItemSync {
	private T SyncObject;

	public T getSyncObject() {
		return SyncObject;
	}

	public void setSyncObject(T SyncObject) {
		this.SyncObject = SyncObject;
	}
}
