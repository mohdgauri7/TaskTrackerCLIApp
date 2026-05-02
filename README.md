# Task CLI Application

This is a simple command-line interface (CLI) application for managing tasks. You can add, update, delete, mark, and list tasks directly from the terminal.

## Features

- **Add a Task:** Add a new task with a description.
- **Update a Task:** Update the description of an existing task.
- **Delete a Task:** Remove a task by its ID.
- **Mark a Task:** Mark a task as "in progress" or "done."
- **List Tasks:** List all tasks or filter them by status (e.g., `todo`, `in progress`, `done`).

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/mohdgauri7/TaskTrackerCLIApp.git
   cd TaskTrackerCLIApp

2. **Compile the source code:**
    ```bash
   javac TaskTracker.java TaskManager.java TaskModel.java Status.java
3. **Run the application:**
    ```bash
   java TaskTracker <command> [arguments]
   ```
## Usage
```bash
# Adding a new task
java TaskTracker add "Buy groceries"
# Output: Task added successfully (ID: 1)

# Updating a task
java TaskTracker update 1 "Buy groceries and cook dinner"
# Output: Task updated successfully (ID: 1)

# Marking a task as in progress
java TaskTracker mark-in-progress 1
# Output: Task marked as in progress (ID: 1)

# Marking a task as done
java TaskTracker mark-done 1
# Output: Task marked as done (ID: 1)

# Marking a task as done
java TaskTracker mark-todo 1
# Output: Task marked as done (ID: 1)

# Listing all tasks
java TaskTracker list all
# Output: List of all tasks

# Listing tasks by status
java TaskTracker list done
java TaskTracker list inprogress
java TaskTracker list notdone

# Deleting a task
java TaskTracker delete 1
# Output: Task deleted successfully (ID: 1)

```