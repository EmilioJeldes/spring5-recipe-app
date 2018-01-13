package team.waps.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import team.waps.recipe.commands.NotesCommand;
import team.waps.recipe.models.Notes;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    private static final Long LONG_ID = 1l;
    private static final String RECIPE_NOTES = "notes";

    NotesToNotesCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNullConverter() {
        converter.convert(null);
    }

    @Test
    public void testEmptyConverter() {
        converter.convert(new Notes());
    }


    @Test
    public void testConvert() {
        // given
        Notes notes = new Notes();
        notes.setRecipeNotes(RECIPE_NOTES);
        notes.setId(LONG_ID);

        // when
        NotesCommand notesCommand = converter.convert(notes);
        assertNotNull(notesCommand);
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
        assertEquals(LONG_ID, notesCommand.getId());
    }
}