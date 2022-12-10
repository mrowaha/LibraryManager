import classes from './AuthorList.module.css'
import AuthorItem from './AuthorItem'

function AuthorList(props) {
    
    return (
        <ul className={classes.list}>
            {
                props.authors.map(author => {
                    return <AuthorItem 
                    key={author.id} 
                    id={author.id} 
                    name={author.name}
                    description={author.description}
                    genres={author.genres}
                    getAuthorAtIndex={props.getAuthorAtIndex}
                    />
                })
            }
        </ul>
    )
}

export default AuthorList;