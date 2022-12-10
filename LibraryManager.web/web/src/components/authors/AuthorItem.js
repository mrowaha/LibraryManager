import {Link} from 'react-router-dom'

import classes from './AuthorItem.module.css'
import Card from '../ui/Card'

function AuthorItem(props) {

    return (
        <li className={classes.item}>
            <Card>
                <div className={classes.heading}>
                    <h1>{props.name}</h1>
                </div>
                <div className={classes.content}>
                    <h3>{props.description}</h3>
                    <h3>Genres Writting For</h3>
                    <ul>
                        {
                            props.genres.map((genre, index) => {
                                return (<li key={index}>
                                    <h4>{genre}</h4>
                                </li>)
                            })
                        }
                    </ul>
                </div>

                <div className={classes.actions}>
                    <Link to={`/authors/${props.id}`}>More Info</Link>        
                </div>
            </Card>
        </li>
    )
}

export default AuthorItem;