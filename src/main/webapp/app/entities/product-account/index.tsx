import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductAccount from './product-account';
import ProductAccountDetail from './product-account-detail';
import ProductAccountUpdate from './product-account-update';
import ProductAccountDeleteDialog from './product-account-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductAccountUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductAccountDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductAccount} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductAccountDeleteDialog} />
  </>
);

export default Routes;
