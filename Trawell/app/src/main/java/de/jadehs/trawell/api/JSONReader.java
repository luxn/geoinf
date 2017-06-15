package de.jadehs.trawell.api;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.models.Accommodation;

/**
 * Created by Lisa Haltermann on 12.06.2017.
 */

public class JSONReader {
    public static List<Accommodation> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public static List<Accommodation> readMessagesArray(JsonReader reader) throws IOException {
        List<Accommodation> messages = new ArrayList<Accommodation>();

        reader.beginArray();
        while (reader.hasNext()) {
//            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    public static void readMessage(JsonReader reader) throws IOException {
        long id = -1;
        String name = null;
        String bewertung = null;
        String adresse = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String value = reader.nextName();
            if (value.equals("name")) {
                name = reader.nextString();
            } else if (value.equals("rating")) {
                bewertung = reader.nextString();
            } else if (value.equals("vicinity")) {
                adresse = reader.nextString();
            } else {
                reader.skipValue();
            }
     }
        reader.endObject();
//        return new Accommodation(id , name,bewertung,adresse);
    }



}
