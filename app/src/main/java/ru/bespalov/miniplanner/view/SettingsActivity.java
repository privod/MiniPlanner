package ru.bespalov.miniplanner.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;

import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import ru.bespalov.miniplanner.R;
import ru.bespalov.miniplanner.db.DatabaseHelper;
import ru.bespalov.miniplanner.db.HelperFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Locale;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || DatabasePreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("language_list"));

            ListPreference listLanguage = (ListPreference) findPreference("language_list");
            listLanguage.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Resources res = getResources();
                    Configuration conf = res.getConfiguration();
                    Locale locale = new Locale(newValue.toString());
                    conf.setLocale(locale);
                    res.updateConfiguration(conf, res.getDisplayMetrics());
                    return true;
                }
            });

        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DatabasePreferenceFragment extends PreferenceFragment {
        final int WRITE_PERMISSION_REQUEST = 0;
        EditTextPreference prefBackUpPath;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_database);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("database_path"));

            prefBackUpPath = (EditTextPreference) findPreference("database_path");
            if (prefBackUpPath.getText().isEmpty()) {
                prefBackUpPath.setText(Environment.getExternalStorageDirectory().getPath()
                        + File.separator
                        + Environment.DIRECTORY_DOWNLOADS
                        + File.separator
                        + DatabaseHelper.DATABASE_NAME
                );
            }

            final File dbFile = getActivity().getDatabasePath(DatabaseHelper.DATABASE_NAME);

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST);

            Preference prefLoad = (Preference) findPreference("database_load");
            prefLoad.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    final File bakFile = new File(prefBackUpPath.getText());
                    if (bakFile.exists()) {
                        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                        ad.setTitle(R.string.pref_title_database_load);
                        ad.setMessage("ВНИМАНИЕ: будет произведена полная загрузка базы данных из файла, текущее состояние данных приложения будет утеряно");
                        ad.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                copyFile(bakFile, dbFile);
                                Toast.makeText(getContext(), R.string.data_loaded, Toast.LENGTH_LONG).show();
                            }
                        });
                        ad.setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Toast.makeText(getContext(), R.string.operation_canceled, Toast.LENGTH_LONG).show();
                            }
                        });
                        ad.show();
                    } else {
                        Toast.makeText(getContext(), String.format(getContext().getString(R.string.not_found_file), bakFile.getPath()), Toast.LENGTH_LONG).show();
                    }

                    return false;
                }
            });

            Preference prefUnload = (Preference) findPreference("database_unload");
            prefUnload.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    if (dbFile.exists()) {
                        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                        ad.setTitle(R.string.file_exist);
                        ad.setMessage(String.format(getContext().getString(R.string.rewrite_file), prefBackUpPath.getText()));
                        ad.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                copyFile(dbFile, new File(prefBackUpPath.getText()));
                                Toast.makeText(getContext(), R.string.data_unloaded, Toast.LENGTH_LONG).show();
                            }
                        });
                        ad.setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                Toast.makeText(getContext(), R.string.operation_canceled, Toast.LENGTH_LONG).show();
                            }
                        });
                        ad.show();
                    }

                    return false;
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void copyFile(File sourceFile, File destFile) {
            if(!destFile.exists()) {
                try {
                    destFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try (
                    FileChannel source = new FileInputStream(sourceFile).getChannel();
                    FileChannel destination = new FileOutputStream(destFile).getChannel()
            ) {
                destination.transferFrom(source, 0, source.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
