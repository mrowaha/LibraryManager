import {useContext} from 'react'

import ContentLoader from 'react-content-loader';

import classes from './Page.module.css'
import AuthorsContext from '../store/author-context';
import AuthorList from '../components/authors/AuthorList';
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

function AllAuthorsPage() {

    const authorsCtx = useContext(AuthorsContext);

    //search filters. Priorty is give to genre filter
    const setName = useByName("", authorsCtx);
    const setGenre = useByGenre("", authorsCtx);

    const searchByNameHandler = (byName) => {
        setName(prev => {return {...prev, value : byName}});   
    }

    const searchByGenreHandler = (byGenre) => {
        setGenre(byGenre);
    }

    let content;
    if (authorsCtx.loading) {
        content = contentWhenLoading;
    } else {
        if (authorsCtx.authors.length <= 0 ) {
            content = <p className={classes.warning_message}>No Authors have been registered</p>
        } else {
            content = <AuthorList 
                       authors={authorsCtx.authors} 
                       getAuthorAtIndex={authorsCtx.getAuthorAtIndex}
                       />
            }
    }

    return (
        <section>
            <div className={classes.header_bar}>
                <h1>All Authors</h1>  
                <SearchBar setSearchByName={searchByNameHandler} setSearchByGenre={searchByGenreHandler}/>              
            </div>
            {content}
        </section>
    )
}


export default AllAuthorsPage;