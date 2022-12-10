import classes from './BookList.module.css'
import BookItem from './BookItem'

function BookList(props) {
    return (
        <ul className={classes.list}>
            {
                props.books.map(book => {
                    return <BookItem 
                    key={book.id} 
                    id={book.id} 
                    name={book.name}
                    description={book.description}
                    genres={book.genres}
                    quantity={book.quantity}
                    author={book.author}
                    getBookAtIndex={props.getBookAtIndex}
                    setOpenAuthorModal = {props.setOpenAuthorModal}
                    setAuthorIdForModal = {props.setAuthorIdForModal}
                    />
                })
            }
        </ul>
    )
}

export default BookList;