package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ConfigurationController;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.ConfigurationInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ConfigurationView;

public class SavedConfigurationsSpinnerAdapter extends ArrayAdapter<ConfigurationInfo> {

    private Activity context;

    private List<ConfigurationInfo> configurationInfos;

    private ConfigurationController controller;

    public SavedConfigurationsSpinnerAdapter(Activity context, List<ConfigurationInfo> configurationInfos, ConfigurationController controller) {
        super(context, R.layout.layout_single_configuration_item, configurationInfos);
        this.context = context;
        this.configurationInfos = configurationInfos;
        this.controller = controller;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent){
        return customGetView(position, view, parent);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        return customGetView(position, view, parent);
    }

    public View customGetView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View itemView = inflater.inflate(R.layout.layout_single_configuration_item, null, true);

        ImageView removeButton = (ImageView) itemView.findViewById(R.id.deleteConfiguration);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.removeAuthenticationConfiguration((ConfigurationView) context, configurationInfos.get(position));
            }
        });

        final TextView name = (TextView) itemView.findViewById(R.id.configNameText);
        final TextView url = (TextView) itemView.findViewById(R.id.urlText);
        final TextView login = (TextView) itemView.findViewById(R.id.loginText);

        final ConfigurationInfo currentConfigurationInfo = configurationInfos.get(position);

        name.setText(currentConfigurationInfo.getName());
        url.setText(currentConfigurationInfo.getUrl());
        login.setText(currentConfigurationInfo.getLogin());

        return itemView;
    }
}
