import {useContext, useState} from 'react'

import ContentLoader from 'react-content-loader';

import classes from './Page.module.css'
import BooksContext from '../store/books-context';
import BookList from '../components/books/BookList'
import AuthorModal from '../components/ui/AuthorModal';
import SearchBar from '../components/ui/SearchBar';

import useByName from '../hooks/useByName';
import useByGenre from '../hooks/useByGenre';

const contentWhenLoading = (
    <ContentLoader
     speed = {2}
     width = {400}
     height = {160}
     viewBox = "0 0 400 160"
     backgroundColor = '#f3f3f3'
     foregroundColor = '#ecebed'
    >

    <rect x="48" y="8" rx="3" ry="3" width="88" height="6" /> 
    <rect x="48" y="26" rx="3" ry="3" width="52" height="6" /> 
    <rect x="0" y="56" rx="3" ry="3" width="410" height="6" /> 
    <rect x="0" y="72" rx="3" ry="3" width="380" height="6" /> 
    <rect x="0" y="88" rx="3" ry="3" width="178" height="6" /> 

    </ContentLoader>
)

function AllBooksPage() {

    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [authorId, setAuthorId] = useState(0);
    const booksCtx = useContext(BooksContext);

    //search filters. Priorty is give to genre filter
    const setName = useByName("", booksCtx);
    const setGenre = useByGenre("", booksCtx);

    const searchByNameHandler = (byName) => {
        setName(prev => {return {...prev, value : byName}});   
    }

    const searchByGenreHandler = (byGenre) => {
        setGenre(byGenre);
    }

    let content;
    if (booksCtx.loading) {
        content = contentWhenLoading;
    } else {
        if (booksCtx.books.length <= 0 ) {
            content = <p className={classes.warning_message}>No Books have been registered</p>
        } else {
            content = <BookList 
                       books={booksCtx.books} 
                       getBookAtIndex={booksCtx.getBookAtIndex}
                       setOpenAuthorModal = {setModalIsOpen}
                       setAuthorIdForModal = {setAuthorId}
                       />
            }
    }

    return (
        <section>
            <div className={classes.header_bar}>
                <h1>All Books</h1>  
                <SearchBar setSearchByName={searchByNameHandler} setSearchByGenre={searchByGenreHandler}/>              
            </div>

            {content}

            {modalIsOpen && 
                <AuthorModal setIsOpen={setModalIsOpen} 
                 authorContent={booksCtx.getBookAuthor(authorId)} 
                 />
            }
        </section>
    )
}


export default AllBooksPage;