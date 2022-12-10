import {Routes, Route, Navigate} from 'react-router-dom'

import { BooksContextProvider } from './store/books-context';
import { AuthorsContextProvider } from './store/author-context';

import AllBooksPage from './pages/AllBooks'
import AllAuthorsPage from './pages/AllAuthors'
import AuthorById from './pages/AuthorById';
import Layout from './components/layout/Layout'

function App() {

    const AuthorsContext_Provider = AuthorsContextProvider;

    return (
        <Layout>
            <Routes>

                <Route path="/authors/:id"
                element={
                    <AuthorsContext_Provider>
                        <AuthorById />
                    </AuthorsContext_Provider>
                    }
                />
            
                <Route path="/authors" 
                 element={
                    <AuthorsContext_Provider>
                        <AllAuthorsPage />
                    </AuthorsContext_Provider>
                    }
                 exact={true}/>
                
                <Route path="/books" 
                 element={
                    <BooksContextProvider>
                        <AllBooksPage />
                    </BooksContextProvider>
                 } 
                 exact={true}/>
                
                <Route path="/" element={<Navigate replace to="/books"/>} exact={true}/>
            
            </Routes>
        </Layout>  
    );
}

export default App;