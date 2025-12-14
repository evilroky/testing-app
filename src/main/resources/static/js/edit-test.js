let newQuestionIndex = 100000;

// добавление нового вопроса
function addQuestion() {
    const container = document.getElementById("questionsContainer");

    const block = document.createElement("div");
    block.classList.add("question-block", "edit-question");

    block.innerHTML = `
        <div class="q-header question-header">
            <h3 class="question-title">Новый вопрос</h3>
        </div>

        <label class="edit-label">Текст вопроса:</label>
        <input type="text" name="new_question_${newQuestionIndex}" required
               class="edit-input question-input">

        <div class="answers-container">
            <div class="answer-row edit-answer-row">
                <input type="text" name="new_answer_${newQuestionIndex}_1"
                       placeholder="Вариант ответа" class="answer-input edit-answer-input">
                <input type="checkbox" name="new_correct_${newQuestionIndex}_1"
                       class="correct-checkbox">
            </div>
            <div class="answer-row edit-answer-row">
                <input type="text" name="new_answer_${newQuestionIndex}_2"
                       placeholder="Вариант ответа" class="answer-input edit-answer-input">
                <input type="checkbox" name="new_correct_${newQuestionIndex}_2"
                       class="correct-checkbox">
            </div>
            <div class="answer-row edit-answer-row">
                <input type="text" name="new_answer_${newQuestionIndex}_3"
                       placeholder="Вариант ответа" class="answer-input edit-answer-input">
                <input type="checkbox" name="new_correct_${newQuestionIndex}_3"
                       class="correct-checkbox">
            </div>
            <div class="answer-row edit-answer-row">
                <input type="text" name="new_answer_${newQuestionIndex}_4"
                       placeholder="Вариант ответа" class="answer-input edit-answer-input">
                <input type="checkbox" name="new_correct_${newQuestionIndex}_4"
                       class="correct-checkbox">
            </div>
        </div>
    `;

    container.appendChild(block);
    newQuestionIndex++;
}

let deletedQuestions = [];

// пометить существующий вопрос на удаление
function markQuestionForDelete(id, btn) {
    console.log("Удаляем вопрос id=", id); // для проверки в консоли

    if (!deletedQuestions.includes(id)) {
        deletedQuestions.push(id);
    }

    const hidden = document.getElementById("deleteQuestions");
    if (hidden) {
        hidden.value = deletedQuestions.join(",");
    } else {
        console.error("Не найден input#deleteQuestions");
    }

    // скрыть блок на UI
    const block = btn.closest(".question-block");
    if (block) {
        block.style.transition = "opacity 0.2s";
        block.style.opacity = "0";
        setTimeout(() => {
            block.style.display = "none";
        }, 200);
    } else {
        console.error("Не найден .question-block для удаления");
    }
}
