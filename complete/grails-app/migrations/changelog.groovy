databaseChangeLog = {

    changeSet(author: 'guide (generated)', id: 'create-author-book-tag') {
        createTable(tableName: 'author') {
            column(autoIncrement: true, name: 'id', type: 'BIGINT') {
                constraints(primaryKey: true, nullable: false)
            }
            column(name: 'version', type: 'BIGINT') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(100)') {
                constraints(nullable: false)
            }
            column(name: 'email', type: 'VARCHAR(255)')
        }

        createTable(tableName: 'book') {
            column(autoIncrement: true, name: 'id', type: 'BIGINT') {
                constraints(primaryKey: true, nullable: false)
            }
            column(name: 'version', type: 'BIGINT') {
                constraints(nullable: false)
            }
            column(name: 'title', type: 'VARCHAR(255)') {
                constraints(nullable: false)
            }
            column(name: 'isbn', type: 'VARCHAR(20)')
            column(name: 'published_on', type: 'DATE')
            column(name: 'author_id', type: 'BIGINT') {
                constraints(nullable: false)
            }
        }

        addForeignKeyConstraint(
            baseTableName: 'book',
            baseColumnNames: 'author_id',
            referencedTableName: 'author',
            referencedColumnNames: 'id',
            constraintName: 'fk_book_author'
        )

        createTable(tableName: 'tag') {
            column(autoIncrement: true, name: 'id', type: 'BIGINT') {
                constraints(primaryKey: true, nullable: false)
            }
            column(name: 'version', type: 'BIGINT') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(50)') {
                constraints(nullable: false, unique: true)
            }
        }

        createTable(tableName: 'book_tag') {
            column(name: 'book_id', type: 'BIGINT') {
                constraints(nullable: false)
            }
            column(name: 'tag_id', type: 'BIGINT') {
                constraints(nullable: false)
            }
        }

        addPrimaryKey(tableName: 'book_tag', columnNames: 'book_id, tag_id', constraintName: 'pk_book_tag')
    }
}
