---
layout: page
title: User Guide
---

CalcConnect is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, CalcConnect can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F08B-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar CalcConnect.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com u/@john t/Tutorial1` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TUTORIAL]` can be used as `n/John Doe t/Tutorial1` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TUTORIAL]…​` can be used as ` ` (i.e. 0 times), `t/Tutorial1`, `t/Tutorial1 t/Tutorial2` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a popup with a help guide of the commands available to users.

![help popup](images/helpPopup.jpg)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL u/TELEHANDLE [t/TUTORIAL]…​`



Examples:
* `add n/John Doe p/98765432 e/johnd@example.com u/@john`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com u/@betsy p/1234567 t/Tutorial1`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [u/TELEHANDLE] [t/TUTORIAL]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tutorial tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tutorial tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords. 
These keywords can include **names**, **emails**, **Telegram handles** or **tutorial groups**.

Format 1: `find KEYWORD [MORE_KEYWORDS]`

Format 2: `find n/[NAME_KEYWORD] e/[EMAIL_KEYWORD] u/[TELEHANDLE_KEYWORD] tg/[TUTORIAL_KEYWORD]`

The first format searches only by name, while the second format allows searching by specific fields. 
You can combine multiple fields in the second format. 

* The search is case-insensitive. e.g `hans` will match `Hans`
* **Partial matches** are supported. e.g. `ann` will match `Annabel`, `Joanna`
* The **order** of the keywords **does not matter**. e.g. `Hans Bo` will match `Bo Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` 
<br>returns `john` and `John Doe`
* `find alex david`
<br>returns `Alex Yeoh`, `David Li`
* `find n/Alex e/example.com` 
<br>returns persons whose names contain `Alex` or whose email addresses contain `example.com`
* `find u/@jake tg/Tutorial2` 
<br>returns persons whose `Telegram handle` contains `@jake` or who are in `Tutorial2`

  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Grading a person: `grade`

Assigns a grade to a person in the address book.
* Grades the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Assignment name must be in enum list.
* Grade must be a positive integer in the range 0-100 (inclusive). 

Format: `grade INDEX n/ASSIGNMENT_NUMBER g/GRADE`

Examples:
* `grade 3 n/Assignment 1 g/97`
* `grade 5 n/Assignment 2 g/80`

### Sorting the person list: `sort`

Sorts the person list currently displayed in the address book based on given sort instructions.

Format: `sort [field]`

* Sorts the person list based on the specified `field`.
* The `field` can be one of the following: `name`, `grade`, `attendance` and `tutorial`.
* Sorting by `name` sorts the persons in **alphabetical order** of their **names**.
* Sorting by `grade` sorts the persons in **descending order** of their **average grades across all assignments**.
* Sorting by `attendance` sorts the persons in **descending order** of their **attendance percentage**.
* Sorting by `tutorial` sorts the persons in **ascending numerical order** of their **tutorial group numbers**.
* If no `field` is specified, the default sorting field is `name`.
* The sorting is done in ascending order.

Examples:
* `sort name` sorts the person list in alphabetical order of names.
* `sort grade` sorts the person list in descending order of average grades.
* `sort attendance` sorts the person list in descending order of attendance percentage.
* `sort tutorial` sorts the person list in ascending order of tutorial group numbers.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/CalcConnect.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL u/TELEHANDLE [t/TUTORIAL]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com u/@james t/Tutorial1`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Grade** | `grade INDEX n/ASSIGNMENT_NUMBER g/GRADE`<br> e.g., `grade 3 n/Assignment 1 g/97`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [u/TELEHANDLE] [t/TUTORIAL]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Sort** | `sort [field]`<br> e.g., `sort grade`
**List** | `list`
**Help** | `help`
