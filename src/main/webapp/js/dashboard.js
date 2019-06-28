function getCategories() {
    return new Promise((resolve, reject) => {
        const request = new XMLHttpRequest()
        request.onload = event => resolve(JSON.parse(request.response))
        request.onerror = event => reject(event)
        request.open('GET', '/api/category')
        request.send()
    })
}

function addCategoryToHtml(category) {
    const template = document.getElementById('category-template').content
    template.categoryId = category.id
    template.querySelector('span').innerText = category.name
    template.querySelector('h6').innerText = category.tagline ? category.tagline : ''
    template.querySelector('p').innerText = category.description ? category.description : ''
    template.querySelector('.edit-card').href = 'edit-category.html?category=' + category.id
    template.querySelector('.show-cards').href = 'show-flashcards.html?category=' + category.id
    template.querySelector('.train-cards').href = 'train-flashcards.html?category=' + category.id

    const categoryHtml = document.importNode(template, true)
    const container = document.getElementById('category-container')
    container.insertBefore(categoryHtml, container.children[container.children.length - 1])
}

async function loadCategories() {
    const categories = await getCategories()
    for (const category of categories) addCategoryToHtml(category)
}

loadCategories()
