package cms.tannhat.classroompro.ui.book;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cms.tannhat.classroompro.R;
import cms.tannhat.classroompro.adapters.BookViewAdapter;

public class BookViewModel extends ViewModel {

    List<BookViewAdapter.BookData> BookList;
    BookViewAdapter bookViewAdapter;
    public BookViewModel(Context context) {
        BookList = new ArrayList<>();
        bookViewAdapter = new BookViewAdapter(context, BookList);
        BookList.add(createBook(context.getString(R.string.literature1), "literature1"));
        BookList.add(createBook(context.getString(R.string.literature2), "literature2"));
        BookList.add(createBook(context.getString(R.string.chemistry), "chemistry"));
        BookList.add(createBook(context.getString(R.string.biological), "biological"));
        BookList.add(createBook(context.getString(R.string.physics), "physics"));
        BookList.add(createBook(context.getString(R.string.physics_formula), "physics_formula"));
        BookList.add(createBook(context.getString(R.string.algorithm), "algorithm"));
        BookList.add(createBook(context.getString(R.string.geometry), "geometry"));
        BookList.add(createBook(context.getString(R.string.mathoutline1), "mathoutline1"));
        BookList.add(createBook(context.getString(R.string.history), "history"));
        BookList.add(createBook(context.getString(R.string.geography), "geography"));
        BookList.add(createBook(context.getString(R.string.civic), "civic"));
        BookList.add(createBook(context.getString(R.string.english), "english"));
    }

    public BookViewAdapter getBookViewAdapter() {
        return bookViewAdapter;
    }

    private BookViewAdapter.BookData createBook(String name, String id) {
        BookViewAdapter.BookData bookmode = new BookViewAdapter.BookData();
        bookmode.Name = name;
        bookmode.ID = id;
        return bookmode;
    }

}