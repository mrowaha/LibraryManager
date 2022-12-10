import classes from './BookItem.module.css'
import Card from '../ui/Card'

function BookItem(props) {
    
    const toggleAuthorModal = () => {
        props.setAuthorIdForModal(props.author.id)
        props.setOpenAuthorModal(true)
    }

    return (
        <li className={classes.item}>
            <Card>
                <div className={classes.heading}>
                    <h1>{props.name}</h1>
                </div>
                <div className={classes.content}>
                    <h3>{props.description}</h3>
                    <h3>Quantity in Shelf : {props.quantity}</h3>
                    <h3>Genres</h3>
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
                    <button onClick={toggleAuthorModal}>View Author Info</button>        
                </div>
            </Card>
        </li>
    )
}

export default BookItem;