const categoryId = new URL(location.href).searchParams.get('category')

function editCategory() {
    const category = {
        name: document.getElementById('name').value,
        tagline: document.getElementById('tagline').value,
        description: document.getElementById('description').value
    }

    const request = new XMLHttpRequest()
    request.onload = event => location.href = 'dashboard.html'
    request.onerror = event => console.error(event)
    request.open('PUT', '/api/category/' + categoryId)
    request.setRequestHeader('Content-Type', 'application/json')
    request.send(JSON.stringify(category))
}

function getCategory() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/category/' + categoryId)
        request.send()
    })
}

async function loadCategory() {
    const category = await getCategory()
    document.getElementById('name').value = category.name
    document.getElementById('tagline').value = category.tagline ? category.tagline : ''
    document.getElementById('description').value = category.description ? category.description : ''
}

loadCategory()
