package service;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeTypeAdapter extends TypeAdapter<LocalTime> {
    private final DateTimeFormatter formatter;

    public LocalTimeTypeAdapter() {
        // Customize the DateTimeFormatter according to your desired format
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(formatter.format(value));
    }

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String timeStr = in.nextString();
        return LocalTime.parse(timeStr, formatter);
    }
}