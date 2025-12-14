let questionIndex = 0;

function addQuestion() {
    const container = document.getElementById("questionsContainer");

    const block = document.createElement("div");
    block.classList.add("question-block");

    block.innerHTML = `
        <h3>Вопрос ${questionIndex + 1}</h3>

        <label>Текст вопроса:</label>
        <input type="text" name="questions[${questionIndex}].text" required>

        <div class="answers">
            ${createAnswerRow(questionIndex, 0)}
            ${createAnswerRow(questionIndex, 1)}
            ${createAnswerRow(questionIndex, 2)}
            ${createAnswerRow(questionIndex, 3)}
        </div>
    `;

    container.appendChild(block);
    questionIndex++;
}

function createAnswerRow(qIndex, aIndex) {
    return `
        <div class="answer-row">
            <input type="text" 
                   name="questions[${qIndex}].answers[${aIndex}].text"
                   placeholder="Вариант ответа" 
                   required>

            <input type="checkbox"
                   name="questions[${qIndex}].answers[${aIndex}].correct"
                   value="true">
        </div>
    `;
}
