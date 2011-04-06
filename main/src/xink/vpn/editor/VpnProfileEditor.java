package xink.vpn.editor;

import xink.vpn.AppException;
import xink.vpn.Constants;
import xink.vpn.R;
import xink.vpn.VpnProfileRepository;
import xink.vpn.VpnSettings;
import xink.vpn.wrapper.InvalidProfileException;
import xink.vpn.wrapper.VpnProfile;
import xink.vpn.wrapper.VpnState;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public abstract class VpnProfileEditor extends Activity {

    private static final String MSG_ARGS = "messageArgs"; //$NON-NLS-1$

    private EditAction editAction;
    private VpnProfile profile;
    private EditText txtVpnName;
    private EditText txtServer;
    private EditText txtDnsSuffices;
    private EditText txtUserName;
    private EditText txtPassword;
    private VpnProfileRepository repository;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpn_profile_editor);

        repository = VpnProfileRepository.getInstance(getApplicationContext());

        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        initWidgets(contentView);

        ScrollView containerView = (ScrollView) findViewById(R.id.editorScrollView);
        containerView.addView(contentView);

        Intent intent = getIntent();
        init(intent);
    }

    private void initWidgets(final ViewGroup content) {
        TextView lblVpnName = new TextView(this);
        lblVpnName.setText(getString(R.string.vpnname)); //$NON-NLS-1$
        content.addView(lblVpnName);

        txtVpnName = new EditText(this);
        content.addView(txtVpnName);

        TextView lblServer = new TextView(this);
        lblServer.setText(getString(R.string.server)); //$NON-NLS-1$
        content.addView(lblServer);

        txtServer = new EditText(this);
        content.addView(txtServer);

        initSpecificWidgets(content);

        TextView lblDnsSuffices = new TextView(this);
        lblDnsSuffices.setText(getString(R.string.dns_suffices)); //$NON-NLS-1$
        content.addView(lblDnsSuffices);

        TextView lblDnsSufficesDesc = new TextView(this);
        lblDnsSufficesDesc.setText(getString(R.string.comma_sep)); //$NON-NLS-1$
        lblDnsSufficesDesc.setTextColor(0xFF999999);
        lblDnsSufficesDesc.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        content.addView(lblDnsSufficesDesc);

        txtDnsSuffices = new EditText(this);
        content.addView(txtDnsSuffices);

        TextView lblUserName = new TextView(this);
        lblUserName.setText(getString(R.string.username)); //$NON-NLS-1$
        content.addView(lblUserName);

        txtUserName = new EditText(this);
        content.addView(txtUserName);

        TextView lblPassword = new TextView(this);
        lblPassword.setText(getString(R.string.password)); //$NON-NLS-1$
        content.addView(lblPassword);

        txtPassword = new EditText(this);
        txtPassword.setTransformationMethod(new PasswordTransformationMethod());
        content.addView(txtPassword);

        initButtons();
    }

    private void initButtons() {
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                onSave();
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                onCancel();
            }
        });
    }

    private void init(final Intent intent) {
        editAction = EditAction.valueOf(intent.getAction());

        switch (editAction) {
        case CREATE:
            profile = createProfile();
            break;
        case EDIT:
            String name = (String) intent.getExtras().get(Constants.KEY_VPN_PROFILE_NAME);
            profile = repository.getProfileByName(name);
            initViewBinding();
            break;
        default:
            throw new AppException("failed to init VpnProfileEditor, unknown editAction: " + editAction); //$NON-NLS-1$
        }

        setTitle(profile.getType().getNameRid());
    }

    private void initViewBinding() {
        txtVpnName.setText(profile.getName());
        txtServer.setText(profile.getServerName());
        txtDnsSuffices.setText(profile.getDomainSuffices());
        txtUserName.setText(profile.getUsername());
        txtPassword.setText(profile.getPassword());
        doBindToViews();
    }

    protected abstract void doBindToViews();

    @Override
    protected Dialog onCreateDialog(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setMessage(""); //$NON-NLS-1$
        return builder.create();
    }

    @Override
    protected void onPrepareDialog(final int id, final Dialog dialog, final Bundle args) {
        Object[] msgArgs = (Object[]) args.getSerializable(MSG_ARGS);
        ((AlertDialog) dialog).setMessage(getString(id, msgArgs));
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }

        txtVpnName.setText(savedInstanceState.getCharSequence("name")); //$NON-NLS-1$
        txtServer.setText(savedInstanceState.getCharSequence("server")); //$NON-NLS-1$
        txtDnsSuffices.setText(savedInstanceState.getCharSequence("dns")); //$NON-NLS-1$
        txtUserName.setText(savedInstanceState.getCharSequence("user")); //$NON-NLS-1$
        txtPassword.setText(savedInstanceState.getCharSequence("password")); //$NON-NLS-1$
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putCharSequence("name", txtVpnName.getText()); //$NON-NLS-1$
        outState.putCharSequence("server", txtServer.getText()); //$NON-NLS-1$
        outState.putCharSequence("dns", txtDnsSuffices.getText()); //$NON-NLS-1$
        outState.putCharSequence("user", txtUserName.getText()); //$NON-NLS-1$
        outState.putCharSequence("password", txtPassword.getText()); //$NON-NLS-1$
    }

    protected void onSave() {
        try {
            populateProfile();

            if (editAction == EditAction.CREATE) {
                repository.addVpnProfile(profile);
            } else {
                repository.checkProfile(profile);
            }

            Intent intent = new Intent(this, VpnSettings.class);
            intent.putExtra(Constants.KEY_VPN_PROFILE_NAME, profile.getName());
            setResult(0, intent);
            finish();

        } catch (InvalidProfileException e) {
            Bundle args = new Bundle();
            args.putSerializable(MSG_ARGS, e.getMessageArgs());
            showDialog(e.getMessageResourceId(), args);
        }
    }

    private void populateProfile() {
        String name = txtVpnName.getText().toString().trim();
        profile.setName(name);
        profile.setServerName(txtServer.getText().toString().trim());
        profile.setDomainSuffices(txtDnsSuffices.getText().toString().trim());
        profile.setUsername(txtUserName.getText().toString().trim());
        profile.setPassword(txtPassword.getText().toString().trim());
        profile.setState(VpnState.IDLE);
        doPopulateProfile();
    }

    protected abstract void doPopulateProfile();

    protected void onCancel() {
        finish();
    }

    protected EditAction getEditAction() {
        return editAction;
    }

    protected abstract void initSpecificWidgets(final ViewGroup content);

    protected abstract VpnProfile createProfile();

    @SuppressWarnings("unchecked")
    protected <T extends VpnProfile> T getProfile() {
        return (T) profile;
    }

}
