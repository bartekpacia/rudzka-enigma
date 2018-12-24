package pl.baftek.enigma;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private EditText inputEditText;
    private EditText numberEditText;
    private Button buttonEncode;
    private TextView outputTextView;
    private ImageButton copyButton;

    int encoding = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.input);
        numberEditText = findViewById(R.id.number_input);
        buttonEncode = findViewById(R.id.button_encode);
        outputTextView = findViewById(R.id.output);
        copyButton = findViewById(R.id.copyButton);
    }

    public void encode(View view) {
        try {
            encoding = Integer.parseInt(numberEditText.getText().toString());
        } catch (NumberFormatException exception) {
            Toast.makeText(this, "Wprowadź przestaw!", Toast.LENGTH_SHORT).show();
        }

        String inputText = inputEditText.getText().toString();

        String newText = "";

        for (char c : inputText.toCharArray()) {
            newText += Character.toString((char) (((c - 'a' + encoding) % 26) + 'a'));
        }

        String finalText = newText.replace('X', ' ');

        outputTextView.setText(finalText);
    }

    public void decode(View view) {
        encoding = Integer.parseInt(numberEditText.getText().toString());

        String inputText = inputEditText.getText().toString();

        String newText = "";

        for (char c : inputText.toCharArray()) {
            newText += Character.toString((char) (((c - 'a' - encoding) % 26) + 'a'));
        }

        // Some fucking rare bugs
        String finalText = newText.replace('P', ' ').replace('_', 'y').replace(']', 'w').replace('^', 'x').replace('`', 'z');

        outputTextView.setText(finalText);
    }

    public void copyToClipboard(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Enigma Code", outputTextView.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
    }

    public void clearEditText(View view) {
        inputEditText.setText("");
    }
}