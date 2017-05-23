package android.ucsc.vaias.Provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.ucsc.vaias.Helper.DBHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Annick on 04/02/2017.
 */

public class ContentProvider extends android.content.ContentProvider {

    public static final Uri CONTENT_URL = Uri.parse("content://android.mission.accidentdetection/elements");

    // Constantes pour identifier les requetes
    private static final int ALLROWS​ = 1;
    private static final int SINGLE_ROW​ = 2;
    // Uri matcher
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher((UriMatcher.NO_MATCH));
        uriMatcher.addURI("today.comeet.android.comeet", "elements", ALLROWS​);
        uriMatcher.addURI("today.comeet.android.comeet", "elements/#", SINGLE_ROW​);
    }

    private DBHelper myDBHelper;

    @Override
    public boolean onCreate() {
        myDBHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        //Open the Database
        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        //parametres de la requete SQL
        String groupBy = null;
        String having = null;

        //construction de la requete
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        //ajout de la table
        queryBuilder.setTables(DBHelper.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW​:
                // Permet de récupérer l'id de la colonne qu'on souhaite récupérer
                String rowid = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(myDBHelper.COL_1 + "=" + rowid);
            default:
                break;
        }
        Cursor cursor = queryBuilder.query(db, strings, s,
                strings1, groupBy, having, s1);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALLROWS​:
                return
                        "vdn.android.cursor.dir/vnd.paad.elemental";
            case SINGLE_ROW​:
                return "vdn.android.cursor.item/vnd.paad.elemental";
            default:
                throw new IllegalArgumentException("URI non reconnue");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        String nullColumnHack = null;

        long id = db.insert(DBHelper.TABLE_NAME, nullColumnHack, contentValues);
        //si valeurs inseré
        Log.d("creation", "provider :" + id);
        if (id > 1) {
            // construit l'uri de la ligne crée
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URL, id);
            // Notifie le changement de données
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //ouverture de la base de donnée
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        // si requete de ligne on limite les retours à la premiere ligne

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW​:
                String rowId = uri.getPathSegments().get(1);
                selection = DBHelper.COL_1 + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");

            default:
                break;
        }

        if (selection == null) {
            selection = "1";
        }

        // On effectue la suppression
        int deleteCount = db.delete(DBHelper.TABLE_NAME, selection, selectionArgs);

        // Notifie le changement des données
        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
