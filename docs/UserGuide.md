---
layout: page
title: User Guide
---

Fine\$$e is a **desktop app for managing finances, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Fine\$\$e can track and help you cultivate good financial habits faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `finesse.jar` from [here](https://www.youtube.com/watch?v=dQw4w9WgXcQ).

1. Copy the file to the folder you want to use as the *home folder* for Fine$$e.

1. Double-click the file to start the app.

1. Type the command in the command box and press Enter to execute it. e.g. typing `help` and pressing Enter will open the help window.

--------------------------------------------------------------------------------------------------------------------

<div style="page-break-after: always;"></div>

## Features

### 1. Expenses

#### Add expense: `add-expense` / `adde`
Adds a new expense to the tracker.

Format: `add-expense d/DESCRIPTION a/AMOUNT`

Examples:
- `add-expense d/Lunch a/3.2` will add an expense with description “Lunch” and amount $3.20.
- `adde d/Bubble Tea a/4.5` will add an expense with description “Bubble Tea” and amount $4.50.

#### List all expenses: `ls-expense` / `lse`

Shows a list of all expenses in the finance tracker.

Format: `ls-expense` / `lse`

#### Delete an expense: `rm-expense` / `rme`

Deletes a specified expense from the finance tracker.

Format: `rm-expense INDEX` / `rme INDEX`

 - Deletes a specified expense at the specified `INDEX` in the finance tracker.
 - The index refers to the index number shown in the displayed expense list.
 - The index **must be a positive integer** 1, 2, 3, ...

Examples:
 - `rm-expense 3` will delete the 3rd expense stored in the finance tracker.
 - `rme 2` will delete the 2nd expense stored in the finance tracker.

 <div style="page-break-after: always;"></div>

### 2. Income

#### Add income: `add-income` / `addi`
Adds a new income to the tracker.

Format: `add-income d/DESCRIPTION a/AMOUNT`

Examples:
- `add-income d/Internship a/1200` will add a new income with description “Internship” and amount $1200.00.
- `addi d/Random Angpao a/10` will add a new income with description “Random Angpao” and amount $10.00.


#### List all income: `ls-income` / `lsi`

Shows a list of all income in the finance tracker.

Format: `ls-income` / `lsi`

#### Delete income: `rm-income` / `rmi`

Deletes a specified income in the finance tracker.

Format: `rm-income INDEX` / `rmi INDEX`

 - Deletes the specified income entry from the finance tracker.
 - The index refers to the index number shown in the displayed income list.
 - The index **must be a positive integer** 1, 2, 3, ...

Examples:
 - `rm-income 2` removes the 2nd income entry stored in the finance tracker.
 - `rmi 3` removes the 3rd income entry stored in the finance tracker.

<div style="page-break-after: always;"></div>

### 3. Savings

#### Show savings: `savings` / `sav`
Shows the amount of money saved for the month.

Format: `savings` / `sav`

### Viewing help: `help`
Shows a message explaining how to access the help page.

Format: `help`

### Exiting the program: `exit`
Exits the program.

Format: `exit`

---

<div style="page-break-after: always;"></div>

## Command Summary

Action | Format | Examples
------|------|--------
Add Expense | `add-expense d/DESCRIPTION a/AMOUNT` <br> `adde d/DESCRIPTION a/AMOUNT` | `add-expense d/Lunch a/3.2`<br>`adde d/BubbleTea a/6`
List Expenses | `ls-expense` <br> `lse`
Delete Expense | `rm-expense INDEX` <br> `rme INDEX` | `rm-expense 3`<br>`rme 2`
Add Income | `add-income d/DESCRIPTION a/AMOUNT` <br> `addi d/DESCRIPTION a/AMOUNT` | `add-income d/Internship a/1000`<br>`addi d/Random Angpao a/10`
List Incomes | `ls-income` <br> `lsi`
Delete Income | `rm-income INDEX` <br> `rm INDEX` | `rm-income 2`<br>`rmi 3`
Check Savings | `savings` <br> `sav`
Help | `help`
Exit | `exit`
