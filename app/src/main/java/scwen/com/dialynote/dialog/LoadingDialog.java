package scwen.com.dialynote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import scwen.com.dialynote.R;


/**
 * 
 * 网络加载等待提示框
 *
 */
public class LoadingDialog extends Dialog {
	private TextView tv_text;


	public LoadingDialog(Context context) {
		super(context, R.style.loadingDialogStyle);
	}

	private LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_loading);
//		LinearLayout linearLayout = this.findViewById(R.id.LinearLayout);
//		tv_text =  findViewById(R.id.tv_text);
//		linearLayout.getBackground().setAlpha(150);
		setContentView(R.layout.dialog);
		tv_text =  findViewById(R.id.id_tv_loadingmsg);
	}

	/**
	 * 为加载进度个对话框设置不同的提示消息
	 * @param message
	 * @return
     */
	public LoadingDialog setMessage(String message) {
		tv_text.setText(message);
		return this;
	}
}
