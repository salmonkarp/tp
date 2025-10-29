---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="570" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="480" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* MA1521 tutors/lecturers
* has a need to manage a significant number of student contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Provides Teaching Assistants with a streamlined, CLI-based management tool that gives them quick and organized access to their assigned students, including profile details, and performance records. TAs can record marks, manage assignments and track attendance without leaving the CLI environment, thus saving time and reducing friction.



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …                                     | I want to …                                                                       | So that I can …                                                                          |
|----------|--------------------------------------------|-----------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| `* * *`  | MA1521 TA using the app for the first time | view the list of available basic commands and their general purpose               | start exploring the app's functionalities                                                |
| `* * *`  | MA1521 TA                                  | assign student grades for assignments and exams                                   | keep track of the student grades                                                         |
| `* * *`  | MA1521 TA                                  | view each student's specific credentials                                          | verify their identity and manage group assignments                                       |
| `* * *`  | MA1521 TA                                  | create student profiles                                                           | add students to my tutorial                                                              |
| `* * *`  | MA1521 TA                                  | delete student profiles                                                           | remove students from my tutorial                                                         |
| `* *`    | MA1521 TA                                  | find a specific student's contact details from their name only                    | save time instead of searching students in my tutorial group one-by-one                  |
| `* *`    | MA1521 TA                                  | mark the attendance of multiple students                                          | calculate the students attendance score                                                  |
| `* *`    | MA1521 TA                                  | unmark the attendance of a student                                                | fix the attendance in the event where I keyed in wrongly                                 |
| `* *`    | MA1521 TA                                  | view the students I have in each of the multiple classes I conduct                | find out who are in each of my tutorial groups                                           |
| `* *`    | MA1521 TA                                  | view a single students performance across assignments and attendance              | track their progress over time                                                           |
| `* *`    | MA1521 TA using the app for the first time | tag students to their tutorial groups                                             | differentiate between my different tutorial groups                                       |
| `* *`    | MA1521 TA using the app for the first time | have the system recognize my command despite a small typo                         | efficiently execute commands despite having small typos                                  |
| `* *`    | MA1521 TA                                  | update student profile                                                            | edit the records of my students                                                          |
| `* *`    | MA1521 TA using the app for the first time | see how I use a specific command (arguments, format, etc.)                        | use a command properly                                                                   |
| `* *`    | MA1521 TA                                  | view a summary of grades for an assignment                                        | find out the overall performance of my class                                             |
| `* *`    | MA1521 TA                                  | sort the list of students I have based on alphabetical order                      | obtain specific orderings for admin tasks                                                |
| `* *`    | MA1521 TA                                  | sort the list of students I have based on overall marks                           | identify a general grade trend as well as students I need to assist more during teaching |
| `*`      | MA1521 TA                                  | get all my students' emails/phone numbers to my clipboard in one command          | instantly paste said details into another application (e.g. email)                       |
| `*`      | CLI Experienced MA1521 TA                  | import previous student data (csv, etc.) into this new application                | seamlessly continue my workflow without excessive data reformatting                      |
| `*`      | Intermediate MA1521 TA user                | add pictures / notes about my students                                            | remember them better and add remarks about them                                          |
| `*`      | Aesthetically oriented MA1521 TA           | change colour theme                                                               | customize the appearance                                                                 |
| `*`      | Impatient MA1521 TA                        | command responses to appear within 1 second                                       | work quickly and efficiently                                                             |
| `*`      | MA1521 TA using the app for the first time | allow the long commands to be broken into multiple parts                          | be more familiarized with the commands                                                   |
| `*`      | MA1521 TA using the app for the first time | see if my command is wrong without clearing out the command if i typed it wrongly | edit my command instead of retyping it                                                   |

### Use cases

(For all use cases below, the **System** is the `CalcConnect` and the **Actor** is the `MA1521 TA`, unless specified otherwise)

**Use case: UC01 - Get Help**

**MSS:**

1.  TA requests a list of all commands.
2.  CalcConnect displays all commands able to be used by the user.

    Use case ends.

**Use case: UC02 - Add Student**

**MSS:**
1.  TA requests to add a student.
2.  CalcConnect adds the student.
3.  CalcConnect displays the updated student list.

    Use case ends.

**Extensions:**
* 1a. The typed command is invalid.

    * 1a1. CalcConnect shows an appropriate error message and the correct format.

      Use case ends.

**Use case: UC03 - Delete Student**

**MSS:**

1.  TA requests to list students.
2.  CalcConnect shows a list of students.
3.  TA requests to delete a specific student in the list.
4.  CalcConnect deletes the student.
5.  CalcConnect displays the updated student list.

    Use case ends.

**Extensions:**

* 2a. The student list is empty.

    * 2a1. CalcConnect alerts the user that the student list is empty.

      Use case ends.

* 3a. The given student index is invalid.

    * 3a1. CalcConnect shows an error message.

      Use case resumes at step 2.

**Use case: UC04 - Assign Grade to Student**

**MSS:**

1.  TA requests to assign a grade for an assessment for a student.
2.  CalcConnect sets the grade for the assessment to the student.

    Use case ends.

**Extensions:**

* 1a. The student list is empty.

    * 1a1. CalcConnect shows an error message.

      Use case ends.

* 1b. The given index, grade or assessment name is invalid.

    * 1b1. CalcConnect shows an appropriate error message.

      Use case resumes at step 1.

**Use case: UC05 - View Student Credentials**

**MSS:**

1.  TA requests to view credentials of a student.
2.  CalcConnect displays the credentials of the student.

    Use case ends.

**Extensions:**

* 1a. The student list is empty.

    * 1a1. CalcConnect shows an error message.

      Use case ends.

**Use case: UC06 - Exit Application**

**MSS:**

1.  TA requests to exit the application.
2.  CalcConnect stops execution and closes.

    Use case ends.

**Use case: UC07 - Assign Tutorial Group to Student**

**MSS:**

1.  TA requests to assign a tutorial group to a student.
2.  CalcConnect assigns the tutorial group to the student.
3.  CalcConnect displays the updated student list.

    Use case ends.

**Extensions:**

* 1a. The student list is empty.

    * 1a1. CalcConnect shows an error message.

      Use case ends.

* 1b. The given index or tutorial group name is invalid.

    * 1b1. CalcConnect shows an error message.

      Use case resumes at step 1.

**Use case: UC08 - View all students in a Tutorial Group**

**MSS:**

1.  TA requests to view all students in a particular tutorial group.
2.  CalcConnect lists out all the students in the tutorial group.

    Use case ends.

**Extensions:**

* 1a. The given tutorial group name is invalid.

    * 1a1. CalcConnect shows an error message.

      Use case resumes at step 1.

* 1b. The tutorial group has no students.

    * 1b1. CalcConnect alerts the user that the tutorial group is empty.

      Use case ends.

**Use case: UC09 - Mark Attendance**

**MSS:**

1.  TA <ins>view all students in a tutorial group (UC08)</ins>.
2.  TA marks attendance of the students present.
3.  CalcConnect shows all the students present and absent in the tutorial group.

    Use case ends.

**Extensions:**

* 2a. The tutorial group has no students.

    * 2a1. CalcConnect shows an error message.

      Use case resumes at step 1.

* 2b. The given student index(es) is/are invalid.

    * 2b1. CalcConnect shows an error message.

      Use case resumes at step 2.

**Use case: UC10 - Edit Student Credentials**

**MSS:**

1.  TA requests to edit a student’s credentials.
2.  CalcConnect updates the student’s credentials.
3.  CalcConnect shows the updated student list.

    Use case ends.

**Extensions:**

* 1a. The given student index is invalid.

    * 1a1. CalcConnect shows an error message.

      Use case resumes at step 1.

### Non-Functional Requirements

1.  The software should work on any _mainstream OS_ platforms (Constraint-Platform-Independent). 
2.  The software should work on a computer that has version `17` of Java (Constraint-Java-Version). 
3.  The software should work without requiring an installer (Constraint-Portable). 
4.  The software should not depend on your own remote server (Constraint-No-Remote-Server). 
5.  The GUI should be usable (i.e., all functions can be used even if the user experience is not optimal) for, 1. resolutions 1280x720 and higher, and, 2. for screen scales 150% (Constraint-Screen-Resolution).
6.  The software should be able to be packaged into a single JAR file (Constraint-Single-File).
7.  The data should be stored locally and should be in a human editable text file (Constraint-Human-Editable-File).
8.  The file size of the product should not exceed 100MB (Constraint-File-Size).
9.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage. 
10. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
11. The system should respond within two seconds for common operations such as querying, finding or sorting a large number of students (potentially hundreds). 
12. The system should be usable by a novice who has never been a Teaching Assistant before.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **MA1521**: Calculus for Computing course, containing typically hundreds of Y1 NUS students
* **TA / Tutor**: A teaching assistant who conducts tutorials for MA1521
* **Professor**: A professor who teaches MA1521, and oversees the tutors and the course management
* **Student**: A student who is enrolled in MA1521, and attends tutorials conducted by tutors
* **Assignment**: A piece of coursework that students need to submit for grading
* **Attendance**: The state of being present in a certain tutorial session
* **Student Credentials**: A student’s name, NUS student number, email address, tutorial group number, etc.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

## **Appendix: Effort**

The total effort spent on active development so far is approximately `>=125` hours.
This includes time spent on design, implementation, testing, documentation, and project management activities.

A small part of the implementation of the Fuzzy Search feature was done through an external library.
[This library](https://commons.apache.org/proper/commons-text/apidocs/org/apache/commons/text/similarity/LevenshteinDistance.html) is from the Apache Commons project and is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

### Difficulty level
The difficulty level of this project is considered to be 'Moderate', tending to occasionally 'High' at the earlier stages of development.

This was due to the need to modify the original commands' implementations significantly to fit the new requirements,
which often had a compound effect on other parts of the codebase, and thus was also difficult to segment amongst members.

Furthermore, multiple new object types have been created to represent new complex attributes.
Given that these attributes (Attendance, Grades) are often non-trivial, they required extensive code and tests to ensure their correctness and robustness.

### Challenges faced
Some of the challenges faced during the development of this project include:
* Initial learning process of the codebase, which was very interconnected prior to making any significant changes.
* Selecting design choices that would best fit the new requirements while minimizing the impact on existing functionalities and making future extensions easier.
* Segmenting work effectively among team members, given the interconnected nature of the codebase.

### Achievements
Some of the general achievements of this project include:
* Successful implementation of all core MVP requirements.
* Implementation of additional features such as Fuzzy Search.
* Aesthetic improvements to the UI to utilize more screen space and enhance user experience.
* Enhanced CLI experience through addition of a navigable command history.
