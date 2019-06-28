function sendStatement(mode) {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('POST', '/db/' + mode)
        request.setRequestHeader('Content-Type', 'text/sql')
        request.send(document.getElementById('sql').value)
    })
}

async function execute(mode) {
    const result = await sendStatement(mode);

    const tblColumns = document.getElementById('columns')
    const tblData = document.getElementById('data')
    while (tblColumns.lastChild) tblColumns.lastChild.remove()
    while (tblData.lastChild) tblData.lastChild.remove()

    for (const column of result.columns) {
        const columnHtml = document.createElement('th')
        columnHtml.innerText = column
        tblColumns.appendChild(columnHtml)
    }

    for (const row of result.data) {
        const rowHtml = document.createElement('tr')
        for (const column of result.columns) {
            const dataHtml = document.createElement('td')
            dataHtml.innerText = row[column]
            rowHtml.appendChild(dataHtml)
        }
        tblData.appendChild(rowHtml)
    }
}

function getAllStatement() {
    document.getElementById('sql').value = 'SELECT f.*, c.* FROM flashcard f\n' +
        'JOIN flashcard2category f2c ON f2c.flashcardId = f.id\n' +
        'JOIN category c ON c.id = f2c.categoryId'
}
