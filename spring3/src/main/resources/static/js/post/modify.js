/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {
	const btnDelete = document.querySelector('button#btnDelete');
	const id = document.querySelector('input#id');
	const form = document.querySelector('form#postModifyForm');
	const btnUpdate = document.querySelector('button#btnUpdate');
	
	btnDelete.addEventListener('click', (e) => {
		e.preventDefault();
		const idV = id.value;
		const result = confirm(`${idV}번 글을 삭제할까요?`);
		if (result) {
			form.action = `/post/delete?id=${idV}`;
			form.method = "post"
			form.submit();
		}
	});
	
	btnUpdate.addEventListener('click', (e) => {
		const result = confirm('수정사항을 업데이트 하시겠습니까?');
		if (result) {
			const title = document.querySelector('input#title');
			const content = document.querySelector('textarea#content');
			const author = document.querySelector('input#author');
			title.setAttribute('name', 'title');
			content.setAttribute('name', 'content');
			id.setAttribute('name', 'id');
			author.setAttribute('name', 'author');
			form.action = "/post/update";
			form.method = "post";
			form.submit();
		} else {
			return;
		}
		
	});
});