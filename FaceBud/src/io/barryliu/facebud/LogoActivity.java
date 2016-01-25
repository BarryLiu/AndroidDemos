package io.barryliu.facebud;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class LogoActivity extends Activity implements OnClickListener {

	private Button man;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);

		initView();

		setLisenter();

		setAnimoation();
	}

	private void setAnimoation() {
		TranslateAnimation animation = new TranslateAnimation(-100f, 0, 0, 0);
		animation.setDuration(2000);
		animation.setFillAfter(true);

		man.setAnimation(animation);
	}

	private void setLisenter() {
		man.setOnClickListener(LogoActivity.this);
	}

	private void initView() {
		man = (Button) findViewById(R.id.man);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.man:
			Intent intent = new Intent(LogoActivity.this, MainActivity.class);

			startActivity(intent);
			break;
		}
	}

}
