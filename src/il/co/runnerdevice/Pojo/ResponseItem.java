package il.co.runnerdevice.Pojo;

public class ResponseItem<T> extends ResponseBase {
	protected T Model;
	public T getModel() {
		return Model;
		}
		public void setModel(T Model) {
		this.Model = Model;
		}
}
