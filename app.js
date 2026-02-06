(() => {
  const STORAGE_KEY = 'github-todo-local';
  const taskInput = document.getElementById('new-task');
  const addButton = document.getElementById('add-task');
  const todoList = document.getElementById('todo-list');
  let tasks = loadTasks();

  function loadTasks() {
    const raw = window.localStorage.getItem(STORAGE_KEY);
    return raw ? JSON.parse(raw) : [];
  }

  function persist() {
    window.localStorage.setItem(STORAGE_KEY, JSON.stringify(tasks));
  }

  function renderTasks() {
    todoList.innerHTML = '';
    if (!tasks.length) {
      const hint = document.createElement('li');
      hint.className = 'px-3 py-2 color-text-muted';
      hint.textContent = 'No tasks yet. Start with something small.';
      todoList.append(hint);
      return;
    }

    tasks.forEach((task) => todoList.appendChild(createTaskElement(task)));
  }

  function createTaskElement(task) {
    const li = document.createElement('li');
    li.className = 'd-flex flex-justify-between flex-items-center px-3 py-2 border color-border-muted rounded-2 mb-2 bg-white';
    li.dataset.taskId = task.id;

    const span = document.createElement('span');
    span.className = 'todo-item text-normal f5 flex-auto';
    span.textContent = task.name;

    const deleteButton = document.createElement('button');
    deleteButton.type = 'button';
    deleteButton.className = 'delete-pill';
    deleteButton.dataset.action = 'delete-task';
    deleteButton.dataset.taskId = task.id;
    deleteButton.setAttribute('aria-label', `Delete ${task.name}`);
    deleteButton.innerHTML = '<i class="fa-solid fa-trash-can" aria-hidden="true"></i>';

    li.append(span, deleteButton);
    return li;
  }

  function addTask(name) {
    const trimmed = name.trim();
    if (!trimmed) {
      return;
    }
    tasks.push({
      id: crypto.randomUUID(),
      name: trimmed,
    });
    persist();
    renderTasks();
    taskInput.value = '';
    taskInput.focus();
  }

  function deleteTask(taskId) {
    tasks = tasks.filter((task) => task.id !== taskId);
    persist();
    renderTasks();
  }

  function handleAdd() {
    addTask(taskInput.value);
  }

  function handleListClick(event) {
    const deleteButton = event.target.closest('button[data-action="delete-task"]');
    if (!deleteButton) {
      return;
    }
    const { taskId } = deleteButton.dataset;
    if (!taskId) {
      return;
    }
    deleteTask(taskId);
  }

  addButton.addEventListener('click', handleAdd);
  taskInput.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();
      handleAdd();
    }
  });
  todoList.addEventListener('click', handleListClick);

  window.addEventListener('DOMContentLoaded', () => {
    tasks = loadTasks();
    renderTasks();
  });
})();
