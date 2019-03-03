package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage {
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    Path getcardFolderFilesPath();

    List<ReadOnlyCardFolder> readCardFolders() throws DataConversionException, IOException;

    void saveCardFolder(ReadOnlyCardFolder cardFolder, int index) throws IOException;

    void saveCardFolders(List<ReadOnlyCardFolder> cardFolders) throws IOException;
}
