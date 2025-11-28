package msku.ceng.madlab.week10;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem; // Menü tıklamaları için gerekli olabilir

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteFragment.OnNoteListInteractionListener {

    boolean displayingEditor = false;
    Note editingNote;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        notes = retrieveNotes();

        if (!displayingEditor) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, NoteFragment.newInstance(notes));
            ft.commit();
        } else {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, EditNoteFragment.newInstance(readContent(editingNote)));
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    /**
     * Notun içeriğini okur.
     * Not: Note sınıfınızda 'getContent()' gibi bir metod varsa onu kullanın.
     */
    private String readContent(Note editingNote) {
        if (editingNote != null) {
            // Eğer Note sınıfınızda içerik döndüren bir metod varsa onu çağırın.
            // Örnek: return editingNote.getContent();
            // Şimdilik hata vermesin diye toString() kullanıyoruz:
            return editingNote.toString();
        }
        return ""; // Not yoksa boş metin döndür
    }

    /**
     * Notları getirir (Veritabanı olmadığı için elle sahte veri ekledik).
     */
    private ArrayList<Note> retrieveNotes() {
        ArrayList<Note> dummyNotes = new ArrayList<>();

        // --- DİKKAT: Note sınıfınızın yapısına göre burayı açın ---
        // Uygulamanın boş açılmaması için örnek notlar ekleyelim:
        /*
        dummyNotes.add(new Note("Ders Notu", "Mobil Programlama çalışılacak", "2023-11-28"));
        dummyNotes.add(new Note("Alışveriş", "Süt ve yumurta al", "2023-11-29"));
        */

        return dummyNotes;
    }

    /**
     * Listeden bir nota tıklandığında çalışır.
     * Editör fragmentını açar.
     */
    @Override
    public void onNoteSelected(Note note) {
        editingNote = note;
        displayingEditor = true;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Tıklanan notun içeriğini alıp EditNoteFragment'a gönderiyoruz
        ft.replace(R.id.container, EditNoteFragment.newInstance(readContent(editingNote)));

        // Geri tuşuna basınca listeye dönebilmek için:
        ft.addToBackStack(null);

        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true; // super yerine true döndürmek genelde daha sağlıklıdır
    }
}